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

// Include C++ game communicator
#include "GameCommunicator.hpp"

// TEMP
#include <iostream>
using namespace std;
// TEMP END

// Create communicator
GameCommunicator communicator = GameCommunicator();

// Open extern C if language is C++
#ifdef __cplusplus
extern "C" {
#endif

// Include self header file
#include "connector.hpp"

EXPORT void test(Variant variant[]) {
	cout << "[Test]" << endl;
	int *i = (int*)variant[0].value;
	double *d = (double*)variant[1].value;
	
	cout << "[Plop]" << endl;
	
	cout << "Variant 0: variant.type=" << (int)variant[0].type << "; variant.value=" << *i << endl;
	cout << "Variant 1: variant.type=" << (double)variant[1].type << "; variant.value=" << *d << endl;
}

EXPORT void init(int nbParameters, char *parameters[]) {
	communicator.init(nbParameters, parameters);
}

EXPORT void addAi(short aiId, char *aiName, char *playerName) {
	communicator.addAi(aiId, aiName, playerName);
}

EXPORT void play() {
	communicator.play();
}

EXPORT void endGame() {
	communicator.endGame();
}

EXPORT void disqualifyAi(char *aiName, char *reason) {
	communicator.disqualifyAi(aiName, reason);
}

// Close extern C
#ifdef __cplusplus
}
#endif

