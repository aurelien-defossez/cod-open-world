#ifndef __CONNECTOR_H__
#define __CONNECTOR_H__

void init(int nbParameters, char *parameters[]);
void addAi(short aiId, char *aiName, char *playerName);
void play();
void endGame();
void disqualifyAi(char *aiName, char *reason);
void performGameFunction(int functionId, int nbParameters, Variant parameters[]);

#endif

