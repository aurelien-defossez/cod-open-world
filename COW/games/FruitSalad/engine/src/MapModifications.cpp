#include "MapModifications.h"

MapModifications::MapModifications()
{
    nbNewObjects = 0;
	matrixNewObjects = NULL;
    nbDeletedObjects = 0;
	matrixDeletedObjects = NULL;
    nbMovedObjects = 0;
	matrixMovedObjects = NULL;
    nbModifiedFruits = 0;
	matrixModifiedFruits = NULL;
    nbModifiedSugarDrops = 0;
	matrixModifiedSugarDrops = NULL;
}

MapModifications::~MapModifications()
{
  	if (matrixNewObjects != NULL)
	{
	  delete matrixNewObjects;
	}
	if (matrixDeletedObjects != NULL)
	{
	  delete matrixDeletedObjects;
	}
	if (matrixMovedObjects != NULL)
	{
	  delete matrixMovedObjects;
	}
	if (matrixModifiedFruits != NULL)
	{
	  delete matrixModifiedFruits;
	}
	if (matrixModifiedSugarDrops != NULL)
	{
	  delete matrixModifiedSugarDrops;
	}
}

void MapModifications::addNewModification(int *newModif)
{
    bool change = false;
    int index = -1;
    // Si new alors que delete avant : move
    for (int i = 0; i < nbDeletedObjects; i++)
    {
        if ((deletedObjects.at(i))[0] == newModif[0])
        {
            int x = deletedObjects.at(i)[1];
            int y = deletedObjects.at(i)[2];
            index = i;
            int *moved = new int[5];
            moved[0] = newModif[0];
            moved[1] = x;
            moved[2] = y;
            moved[3] = newModif[1];
            moved[4] = newModif[2];
            movedObjects.push_back(moved);
			delete newModif;
            nbMovedObjects++;
            change = true;
            break;
        }
    }
    if (change == false)
    {
        newObjects.push_back(newModif);
        nbNewObjects++;
    }
    else
    {
        deletedObjects.erase(deletedObjects.begin()+index-1);
        nbDeletedObjects--;
    }
}

void MapModifications::addUpdatedModificationObject(int *newModif)
{
    bool change = false;

    // Si update alors que update avant : update
    for (int i = 0; i < nbModifiedFruits; i++)
    {
        if ((modifiedFruits.at(i))[0] == newModif[0])
        {
            modifiedFruits[i][1] = newModif[1];
            modifiedFruits[i][2] = newModif[2];
            change = true;
            break;
        }
    }
    if (change == false)
    {
        modifiedFruits.push_back(newModif);
        nbModifiedFruits++;
    }
}

void MapModifications::addUpdatedModificationSugar(int *newModif)
{
    bool change = false;

    // Si update alors que update avant : update
    for (int i = 0; i < nbModifiedSugarDrops; i++)
    {
        if (modifiedSugarDrops.at(i)[0] == newModif[0])
        {
            modifiedSugarDrops[i][1] = newModif[1];
            change = true;
            break;
        }
    }
    if (change == false)
    {
        modifiedSugarDrops.push_back(newModif);
        nbModifiedSugarDrops++;
    }
}

void MapModifications::addDeletedModification(int *newModif)
{
    bool change = false;
    int index = -1;
    // Si delete alors que new avant : Rien
    for (int i = 0; i < nbNewObjects; i++)
    {
        if ((newObjects.at(i))[0] == newModif[0])
        {
            index = i;
            change = true;
            break;
        }
    }
    if (change == true)
    {
        newObjects.erase(newObjects.begin()+index-1);
        nbNewObjects--;
        return;
    }
    // Si delete alors que move : delete sur position initiale
    for (int i = 0; i < nbMovedObjects; i++)
    {
        if ((movedObjects.at(i))[0] == newModif[0])
        {
            int x = movedObjects.at(i)[1];
            int y = movedObjects.at(i)[2];
            index = i;
            newModif[1] = x;
            newModif[2] = y;
            deletedObjects.push_back(newModif);
            nbDeletedObjects++;
            change = true;
            break;
        }
    }
    if (change == true)
    {
        movedObjects.erase(movedObjects.begin()+index-1);
        nbMovedObjects--;
        return;
    }
    // Si delete alors que updateFruit : delete
    for (int i = 0; i < nbModifiedFruits; i++)
    {
        if ((modifiedFruits.at(i))[0] == newModif[0])
        {
            index = i;
            deletedObjects.push_back(newModif);
            nbDeletedObjects++;
            change = true;
            break;
        }
    }
    if (change == true)
    {
        modifiedFruits.erase(modifiedFruits.begin()+index-1);
        nbModifiedFruits--;
        return;
    }
    // Si delete alors que updateSugarDrop : delete
    for (int i = 0; i < nbModifiedSugarDrops; i++)
    {
        if ((modifiedSugarDrops.at(i))[0] == newModif[0])
        {
            index = i;
            deletedObjects.push_back(newModif);
            nbDeletedObjects++;
            change = true;
            break;
        }
    }
    if (change == true)
    {
        modifiedSugarDrops.erase(modifiedSugarDrops.begin()+index-1);
        nbModifiedSugarDrops--;
        return;
    }
    // Si normal, on ajoute à deleted
    else
    {
        deletedObjects.push_back(newModif);
        nbDeletedObjects++;
    }
}

void MapModifications::addMovedModification(int *newModif)
{
    bool change = false;

    // Si move alors que move avant : move
    for (int i = 0; i < nbMovedObjects; i++)
    {
        if ((movedObjects.at(i))[0] == newModif[0])
        {
            movedObjects[i][3] = newModif[3];
            movedObjects[i][4] = newModif[4];
            change = true;
            break;
        }
    }
    // Si move alors que new avant : new position finale

    for (int i = 0; i < nbNewObjects; i++)
    {
        if ((newObjects.at(i))[0] == newModif[0])
        {
            newObjects[i][1] = newModif[3];
            newObjects[i][2] = newModif[4];
            change = true;
            break;
        }
    }

    if (change == false)
    {
        movedObjects.push_back(newModif);
        nbMovedObjects++;
    }
}

IntMatrix2* MapModifications::getNewObjects()
{
	if (matrixNewObjects != NULL)
	{
	  delete matrixNewObjects;
	}
	matrixNewObjects = new IntMatrix2(nbNewObjects, 5);
	for (int i = 0; i < nbNewObjects; i++)
    {
		matrixNewObjects->at(i,OBJECT_ID) = newObjects[i][0];
		matrixNewObjects->at(i,OBJECT_X) = newObjects[i][1];
		matrixNewObjects->at(i,OBJECT_Y) = newObjects[i][2];
		matrixNewObjects->at(i,OBJECT_TYPE) = newObjects[i][3];
		matrixNewObjects->at(i,OBJECT_INFO) = newObjects[i][4];
    }
	return matrixNewObjects;
}

IntMatrix1* MapModifications::getDeletedObjects()
{
	if (matrixDeletedObjects != NULL)
	{
	  delete matrixDeletedObjects;
	}
	matrixDeletedObjects = new IntMatrix1(nbDeletedObjects);
	for (int i = 0; i < nbDeletedObjects; i++)
    {
		matrixDeletedObjects->at(i) = deletedObjects[i][0];
    }
	return matrixDeletedObjects;
}

IntMatrix2* MapModifications::getMovedObjects()
{
	if (matrixMovedObjects != NULL)
	{
	  delete matrixMovedObjects;
	}
	matrixMovedObjects = new IntMatrix2(nbMovedObjects, 5);
	for (int i = 0; i < nbMovedObjects; i++)
    {
		matrixMovedObjects->at(i,OBJECT_ID) = movedObjects[i][0];
		matrixMovedObjects->at(i,OBJECT_X) = movedObjects[i][1];
		matrixMovedObjects->at(i,OBJECT_Y) = movedObjects[i][2];
		matrixMovedObjects->at(i,OBJECT_NEW_X) = movedObjects[i][3];
		matrixMovedObjects->at(i,OBJECT_NEW_Y) = movedObjects[i][4];
    }
	return matrixMovedObjects;
}

IntMatrix2* MapModifications::getModifiedFruits()
{
	if (matrixModifiedFruits != NULL)
	{
	  delete matrixModifiedFruits;
	}
	matrixModifiedFruits = new IntMatrix2(nbModifiedFruits, 3);
	for (int i = 0; i < nbModifiedFruits; i++)
    {
		matrixModifiedFruits->at(i,OBJECT_ID) = modifiedFruits[i][0];
		matrixModifiedFruits->at(i,OBJECT_LIFE) = modifiedFruits[i][1];
		matrixModifiedFruits->at(i,OBJECT_DEFENSE) = modifiedFruits[i][2];
    }
	return matrixModifiedFruits;
}

IntMatrix2* MapModifications::getModifiedSugarDrops()
{
	if (matrixModifiedSugarDrops != NULL)
	{
	  delete matrixModifiedSugarDrops;
	}
	matrixModifiedSugarDrops = new IntMatrix2(nbModifiedSugarDrops, 2);
	for (int i = 0; i < nbModifiedSugarDrops; i++)
    {
		matrixModifiedSugarDrops->at(i,OBJECT_ID) = modifiedSugarDrops[i][0];
		matrixModifiedSugarDrops->at(i,OBJECT_SUGAR) = modifiedSugarDrops[i][1];
    }
	return matrixModifiedSugarDrops;
}

void MapModifications::reset()
{
  /*std::vector<int*>::iterator it;
  for(it=newObjects.begin(); it!=newObjects.end(); it++)
  {
	delete[] *it;
  }*/
  newObjects.clear();
  /*for(it=modifiedFruits.begin(); it!=modifiedFruits.end(); it++)
  {
	delete[] *it;
  }*/
  modifiedFruits.clear();
  /*for(it=modifiedSugarDrops.begin(); it!=modifiedSugarDrops.end(); it++)
  {
	delete[] *it;
  }*/
  modifiedSugarDrops.clear();
  /*for(it=movedFruits.begin(); it!=movedFruits.end(); it++)
  {
	delete[] *it;
  }*/
  movedObjects.clear();
  /*for(it=deletedObjects.begin(); it!=deletedObjects.end(); it++)
  {
	delete[] *it;
  }*/
  deletedObjects.clear();
  nbNewObjects = 0;
    nbDeletedObjects = 0;
    nbMovedObjects = 0;
    nbModifiedFruits = 0;
    nbModifiedSugarDrops = 0;
}

std::string MapModifications::printC()
{
    std::string str;
    /*
    str += "\n nbNewObjects- "+std::string::number(nbNewObjects);
    str += " - "+std::string::number(newObjects.size());

    str += "\n nbDeletedObjects- "+std::string::number(nbDeletedObjects);
    str += " - "+std::string::number(deletedObjects.size());

    str += "\n nbMovedFruits- "+std::string::number(nbMovedFruits);
    str += " - "+std::string::number(movedFruits.size());

    str += "\n nbModifiedFruits- "+std::string::number(nbModifiedFruits);
    str += " - "+std::string::number(modifiedFruits.size());

    str += "\n nbModifiedSugarDrops- "+std::string::number(nbModifiedSugarDrops);
    str += " - "+std::string::number(modifiedSugarDrops.size());

    if (nbNewObjects == 1)
    {
        str += "\n"+std::string::number(newObjects.at(0).size());
        str += "\n"+std::string::number(newObjects.at(0)[0])
                  +"\n"+std::string::number(newObjects.at(0)[1])
                  +"\n"+std::string::number(newObjects.at(0)[2]);
                  //+"\n"+std::string::number(modifiedFruits.at(0)[3])
                  //+"\n"+std::string::number(modifiedFruits.at(0)[4]);
    }
    */
    return str;
}
