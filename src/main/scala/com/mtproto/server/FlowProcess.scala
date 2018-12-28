package com.mtproto.server

import akka.stream.stage.{GraphStage, GraphStageLogic, InHandler, OutHandler}
import akka.stream.{Attributes, FlowShape, Inlet, Outlet}
import com.mtproto.messages._
import com.typesafe.scalalogging.StrictLogging

sealed trait FlowProcessStep

case object Finish extends FlowProcessStep

case object WaitingForReqPqMessage extends FlowProcessStep

case object WaitingForReqDHParamsMessage extends FlowProcessStep

class FlowProcess extends GraphStage[FlowShape[UnencryptedMessage, ResponseMessage]] with StrictLogging {

  val input: Inlet[UnencryptedMessage] = Inlet[UnencryptedMessage]("input")
  val output: Outlet[ResponseMessage] = Outlet[ResponseMessage]("output")

  override def createLogic(inheritedAttributes: Attributes): GraphStageLogic = new GraphStageLogic(shape) {
    private var currentStep: FlowProcessStep = WaitingForReqPqMessage

    setHandler(input, new InHandler {
      override def onPush(): Unit = {
        flowProcessHandler(currentStep, grab(input).messageData) match {
          case Right((nextStep, responseMessage)) =>
            nextStep match {
              case Finish => completeStage()
              case _ =>
                currentStep = nextStep
                responseMessage.foreach(message => push(output, message))
            }
          case Left(error) => logger.error(error)
        }
      }
    })
    setHandler(output, new OutHandler {
      override def onPull(): Unit = pull(input)
    })
  }

  override val shape: FlowShape[UnencryptedMessage, ResponseMessage] = FlowShape.of(input, output)

  private def flowProcessHandler(step: FlowProcessStep, message: Message): Either[String, (FlowProcessStep, Option[ResponseMessage])] = {
    step match {
      case WaitingForReqPqMessage => message match {
        case ReqPqMessage(nonce) => Right(WaitingForReqDHParamsMessage -> Some(ResPqMessage.createRandom.copy(nonce = nonce)))
        case _ => Left("Waiting for ReqPqMessage message...")
      }
      case WaitingForReqDHParamsMessage => message match {
        case _: ReqDHParamsMessage => Right(Finish -> None)
        case _ => Left("Waiting for ReqDHParamsMessage message...")
      }
    }
  }

}
