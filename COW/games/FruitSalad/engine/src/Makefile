
objects = API.o Chest.o Equipment.o GameEngine.o main.o MapLoader.o OwnedBuilding.o Peeler.o SugarDrop.o Weapon.o Building.o Entity.o Fruit.o Loader.o Map.o MapModifications.o OwnedEntity.o Player.o SugarFountain.o
sources = API.cpp Chest.cpp Equipment.cpp GameEngine.cpp main.cpp MapLoader.cpp OwnedBuilding.cpp Peeler.cpp SugarDrop.cpp Weapon.cpp Building.cpp Entity.cpp Fruit.cpp Loader.cpp Map.cpp MapModifications.cpp OwnedEntity.cpp Player.cpp SugarFountain.cpp


# now give target as lab1 with objects as variable dependencies + command line

libgame.so: $(objects) 
	@(cd ../connector && make)
	g++ -shared $(objects) \
	-o ../libgame.so \
	-I../connector \
	-I../../../../resources/cpp/commons/src \
	-I../../../../resources/cpp/game/src \
	-L../ -lcommunicator

# list the dependencies for object files - those header files which help build objects

%.o: %.h 

# how to build all object files from all dependent source files

$(objects): $(sources)
	clear
	g++ -c $(sources)

clean:
	rm $(objects) libgame.so
