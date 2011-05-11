#ifndef __CONNECTOR_H__
#define __CONNECTOR_H__

void hello();
int doubleThis(int value);
double sendBack(double value);

typedef void testCallBack(double);
void registerCallback(testCallBack callback);

#endif

