package org.aurora.domain

sealed trait DomainError:
    val message: String 

final case class RepositoryError(cause: Throwable) extends DomainError:
  override val message: String = s"RepositoryError: ${cause.getMessage}"
final case class ValidationError(_message: String)  extends DomainError:
  override val message: String = s"ValidationError: $_message"
case object NotFoundError                          extends DomainError:
  override val message: String = "NotFoundError: The requested resource was not found."
