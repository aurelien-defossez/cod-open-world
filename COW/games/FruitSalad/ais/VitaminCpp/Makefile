all:
	clear
	
	@(cd ../../api/cpp/connector && make)
	
	@echo Compiling ai...
	g++ -std=c++0x -m32 -shared src/Ai.cpp \
	-o libai.so \
	-I../../api/cpp/connector \
	-I../../../../resources/cpp/commons/src \
	-I../../../../resources/cpp/ai/src \
	-L../../api/cpp/ -lcommunicator
	
	@ cp libai.so libai.dylib

