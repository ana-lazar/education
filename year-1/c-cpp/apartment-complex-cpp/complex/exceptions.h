#pragma once

#include <iostream>
#include <string>

namespace mystd {

	class Exception {
	private:
		const string message;
	public:
		Exception(const string message) : message{ message } { }

		const string what() const { return message; }
	};

	class LogicError : public Exception {
	public:
		LogicError(const string message) : Exception(message) { }
	};

	class RepositoryError : public LogicError {
	public:
		RepositoryError(const string message) : LogicError(message) { }
	};

	class ValidatorError : public LogicError {
	public:
		ValidatorError(const string message) : LogicError(message) { }
	};

}