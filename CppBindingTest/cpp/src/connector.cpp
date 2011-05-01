// Windows specific case handling
#ifdef _WIN32
	#ifndef UNICODE
		#define UNICODE
	#endif
	#define WIN32_LEAN_AND_MEAN
	#include <windows.h>
	#define EXPORT __declspec(dllexport)
#else
	#define EXPORT
#endif

// Include specific AI interface and Ai class
#include "Ai.hpp"

Ai ai = Ai();

// Open extern C if language is C++
#ifdef __cplusplus
extern "C" {
#endif

// Include self header file
#include "connector.hpp"

testCallBack *callback;

EXPORT void hello() {
	ai.hello();
}

EXPORT int doubleThis(int value) {
	return ai.doubleThis(value);
}

EXPORT double sendBack(double value) {
	callback(value);
}

EXPORT void registerCallback(testCallBack _callback) {
	callback = _callback;
}

// Close extern C
#ifdef __cplusplus
}
#endif

