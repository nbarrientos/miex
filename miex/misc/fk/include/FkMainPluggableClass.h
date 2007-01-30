/****************************************************************************
*                          fk: Un framework Modular                         * 
*                      Manuel Roberto Berdasco Mart�nez                     *
*                                                                           *
*                               Directores:                                 *
*                      El�as Fern�ndez-Combarro �lvarez                     *
*                           Jos� Ranilla Pastor                             *
*                                                                           *
*                                  v1.0                                     *
*                               12/11/2006                                  *
*****************************************************************************/

/* Clase punto de inicio de una aplicaci�n modular */

#ifndef FKMAINPLUGGABLECLASS_H
#define FKMAINPLUGGABLECLASS_H

// Includes de sistema


// Includes de proyecto
#include "FkPluggableClass.h"

class FkMainPluggableClass : public FkPluggableClass 
{
public:
	virtual int Main() { return 0; };
	virtual ~FkMainPluggableClass() {};
};

#endif 
