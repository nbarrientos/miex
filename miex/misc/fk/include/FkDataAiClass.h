/****************************************************************************
*                          fk: Un framework Modular                         * 
*                      Manuel Roberto Berdasco Martínez                     *
*                                                                           *
*                               Directores:                                 *
*                      Elías Fernández-Combarro Álvarez                     *
*                           José Ranilla Pastor                             *
*                                                                           *
*                                  v1.0                                     *
*                               12/11/2006                                  *
*****************************************************************************/

/* Interfaz del tipo de Dato devuelto por la función NewDataPlug */

#ifndef FKDATAAICLASS_H
#define FKDATAAICLASS_H

using namespace std;

//Includes de sistema
#include <iostream>

 
//Includes de Proyecto


class FkDataAiClass
{
public:	
	virtual ~FkDataAiClass() {};

	virtual operator int() = 0;
	virtual operator char() = 0;
	virtual operator short() = 0;
	virtual operator long() = 0;
	
	virtual operator unsigned int() = 0;
	virtual operator unsigned char() = 0;
	virtual operator unsigned short() = 0;
	virtual operator unsigned long() = 0;
	
	virtual operator float() = 0;
	virtual operator double() = 0;

	virtual operator const char*() = 0;
	virtual operator bool() = 0; 
};


#endif
 
