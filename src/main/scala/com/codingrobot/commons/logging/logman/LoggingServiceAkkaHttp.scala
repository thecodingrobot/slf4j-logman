package com.codingrobot.commons.logging.logman

import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.unmarshalling.FromRequestUnmarshaller
import akka.http.scaladsl.marshalling.ToResponseMarshaller
import akka.http.scaladsl.model.StatusCodes

import scala.concurrent.ExecutionContext

/**
  * Rest service for managing logging on runtime
  */
class LoggingServiceAkkaHttp(path: String = "admin/logging")(implicit marsh: ToResponseMarshaller[Seq[LoggerInfo]], unmarsh: FromRequestUnmarshaller[Seq[LoggerInfo]]) {

  def route(implicit ec: ExecutionContext): Route = {
    pathPrefix(path) {
      get {
        parameters('filter.?) { filter =>
          complete {
            LoggingService.find(filter)
          }
        }
      } ~
        post {
          entity(as[Seq[LoggerInfo]]) {
            loggerInfos => {
              LoggingService.setLevelLoggers(loggerInfos)
              complete(StatusCodes.OK)
            }
          }
        }
    }
  }
}