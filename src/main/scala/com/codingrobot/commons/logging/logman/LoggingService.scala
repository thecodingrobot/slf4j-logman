package com.codingrobot.commons.logging.logman

import ch.qos.logback.classic.{Level, Logger, LoggerContext}
import org.slf4j.LoggerFactory

import scala.collection.JavaConversions._

/**
  * Created by Nikolai Spasov <ns@codingrobot.com> on 06/09/2016.
  */
case class LoggerInfo(name: String, level: String)

object LoggingService {
  private val parentField = {
    val f = classOf[Logger].getDeclaredField("parent")
    f.setAccessible(true)
    f
  }

  private def parent(logger: Logger): Logger = {
    parentField.get(logger).asInstanceOf[Logger]
  }

  def logLevel(logger: Logger): Level = {
    if (logger.getLevel == null) logLevel(parent(logger)) else logger.getLevel
  }

  /**
    * Return available loggers with their respective levels
    *
    * @param filter Optional filter for the logger names
    */
  def find(filter: Option[String]): Seq[LoggerInfo] = {
    val context = LoggerFactory.getILoggerFactory.asInstanceOf[LoggerContext]
    val list = context.getLoggerList
      .map(l => LoggerInfo(l.getName, LoggingService.logLevel(l).toString))
      .sortBy(_.name)

    filter.map {
      f => list.filter(_.name.toLowerCase.contains(f.toLowerCase))
    }.getOrElse(list)
  }

  /**
    * Sets logger levels
    */
  def setLevelLoggers(loggerInfo: Seq[LoggerInfo]): Unit = {
    val context: LoggerContext = LoggerFactory.getILoggerFactory.asInstanceOf[LoggerContext]
    loggerInfo foreach {
      l => context.getLogger(l.name).setLevel(Level.toLevel(l.level.toUpperCase))
    }
  }
}
