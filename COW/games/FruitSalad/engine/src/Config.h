#ifndef HEADER_CONFIG
#define HEADER_CONFIG

//includes
#include <vector>
#include <utility>
#include <map>
#include <set>
#include <string>
#include <sstream>

//Buildings
#define BUILDING_VITAMIN_SOURCE 12
#define BUILDING_SUGAR_TREE 13
#define BUILDING_JUICE_BARREL 14
#define BUILDING_SUGAR_BOWL 15
#define BUILDING_FRUCTIFICATION_TANK 16

//Fruits
#define FRUIT_CHERRY 17
#define FRUIT_KIWI 18
#define FRUIT_NUT 19

//Equipments
#define EQUIPMENT_TEA_SPOON 20
#define EQUIPMENT_TOOTHPICK 21
#define EQUIPMENT_CUTTER 22
#define EQUIPMENT_LIGHTER 23
#define EQUIPMENT_LEMONER 24
#define EQUIPMENT_SALT_SNIPER 25
#define EQUIPMENT_PEELER 26
#define EQUIPMENT_JUICE_NEEDLE 27
#define EQUIPMENT_RELOADER 28

//Divers
#define CHEST 29
#define SUGAR_DROP 30
#define NOTHING 10
#define WALL 11

//Return values: Successes
#define OK 1
#define HIT 2
#define SPLATCHED 3
#define SOME_SUGAR_TAKEN 4
#define ALL_SUGAR_TAKEN 5
#define LIFE_GAINED 6
#define DEFENSE_GAINED 7
#define RELOADED 8

//Return values: Errors
#define UNKNOWN_OBJECT -101
#define NOT_FRUIT -102
#define NOT_EQUIPMENT -103
#define NOT_CHEST -104
#define NOT_SUGAR_DROP -105
#define NOT_VALID_TARGET -106
#define NOT_OWNER -107
#define NOT_EQUIPPED -108
#define NOT_VALID_POSITION -109
#define NOT_VALID_DESTINATION -110
#define NOT_VALID_QUANTITY -111
#define NOT_ENOUGH_SUGAR -112
#define NOT_ENOUGH_VITAMIN -113
#define NOT_ON_THE_FLOOR -114
#define NO_MORE_ACTIONS -115
#define INVALID_TYPE -116
#define TOO_FAR -117
#define TOO_HEAVY -118
#define LIMIT_REACHED -119
#define OBSTACLE_PRESENT -120
#define SUGAR_WALLET_FULL -121
#define SOURCE_FULL -122
#define HEALTHY -123
#define NOT_VALID_COLOR -124


#endif
