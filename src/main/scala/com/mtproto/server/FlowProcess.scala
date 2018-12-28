package com.mtproto.server

import akka.stream.stage.{GraphStage, GraphStageLogic}
import akka.stream.{Attributes, FlowShape, Inlet, Outlet}
import com.mtproto.messages.UnencryptedMessage
import com.typesafe.scalalogging.StrictLogging

class FlowProcess extends GraphStage[FlowShape[UnencryptedMessage, UnencryptedMessage]] with StrictLogging {

  val input: Inlet[UnencryptedMessage] = Inlet[UnencryptedMessage]("input")
  val output: Outlet[UnencryptedMessage] = Outlet[UnencryptedMessage]("output")

  override def createLogic(inheritedAttributes: Attributes): GraphStageLogic = new GraphStageLogic(shape) {

  }

  override val shape: FlowShape[UnencryptedMessage, UnencryptedMessage] = FlowShape.of(input, output)

}
