all:
	clear
	
	@(cd ../../api/cpp/connector && make)
	
	@echo Compiling game...
	g++ -shared src/Ai.cpp \
	-o libai.so \
	-I../../api/cpp/connector \
	-I../../../../resources/cpp/commons/src \
	-I../../../../resources/cpp/ai/src \
	-L../../api/cpp/ -lcommunicator
