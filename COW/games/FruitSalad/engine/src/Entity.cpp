#include "Entity.h"

Entity::Entity(Position p, int idE, int typeE)
{
    position = Position(p.first, p.second);
    id = idE;
    type = typeE;
}


int Entity::getType()
{
    return type;
}

int Entity::getId()
{
    return id;
}

Position Entity::getPosition()
{
    return position;
}

int Entity::maximumOffset(Entity *target)
{
    return std::max(abs(target->getPosition().first - getPosition().first),
					abs(target->getPosition().second - getPosition().second));
}

bool Entity::isNearby(Entity *target)
{
    return (maximumOffset(target) <= 1);
}

void Entity::setPosition(int xN, int yN)
{
    position = Position(xN, yN);
}

bool Entity::isObstacle()
{
	switch (type) {
		case FRUIT_CHERRY :
			return true;
			break;
		case FRUIT_KIWI:
			return true;
			break;
		case FRUIT_NUT:
			return true;
			break;
		case BUILDING_VITAMIN_SOURCE :
			return true;
			break;
		case BUILDING_SUGAR_TREE :
			return true;
			break;
		case BUILDING_JUICE_BARREL :
			return true;
			break;
		case BUILDING_SUGAR_BOWL :
			return true;
			break;
		case BUILDING_FRUCTIFICATION_TANK :
			return true;
			break;
		case EQUIPMENT_TEA_SPOON :
			return false;
			break;
		case EQUIPMENT_TOOTHPICK :
			return false;
			break;
		case EQUIPMENT_CUTTER :
			return false;
			break;
		case EQUIPMENT_LIGHTER :
			return false;
			break;
		case EQUIPMENT_LEMONER :
			return false;
			break;
		case EQUIPMENT_SALT_SNIPER :
			return false;
			break;
		case EQUIPMENT_PEELER :
			return false;
			break;
		case EQUIPMENT_JUICE_NEEDLE :
			return false;
			break;
		case EQUIPMENT_RELOADER :
			return false;
			break;
		case CHEST :
			return true;
			break;
		case SUGAR_DROP :
			return false;
			break;
		default :
			return false;
			break;
	}
}

std::string Entity::printC()
{
    std::string str;
    std::stringstream out;
    out << getPosition().first;
    str += "\nPosition - "+out.str();
    out.str("");
    out << getPosition().second;
    str += "\nPosition - "+out.str();
    out.str("");
    out << id;
    str += "\nId - "+out.str();
    out.str("");
    out << type;
    str += "\nType - "+out.str();
    out.str("");

    return str;
}
