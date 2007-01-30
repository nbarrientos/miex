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


/* Interfaz de Comunicacion de Fk con los módulos */

#ifndef FKAICLASS_H
#define FKAICLASS_H

// Includes de sistema


// Includes de proyecto
#include "FkPluggableClass.h"
#include "FkDataAiClass.h"

class FkAiClass 
{
public:
	virtual FkPluggableClass* NewCodePlug(	const char* name, 
														const char* type,
														const char* desc,
														const char* qualify) = 0;

	virtual FkPluggableClass* NewCode(	const char* name, 
													const char* type,
													const char* path,
													const char* qualify) = 0;
	
	virtual FkDataAiClass& NewDataPlug(	const char* name,
													const char* type, 
													const char* desc,
													const char* qualify) = 0;
													
	virtual void DelCode(FkPluggableClass* instance,
								const char* ptrname,
								const char* qualify)=0;	
								
	virtual ostream& out(unsigned int Level) = 0;
	
	virtual ostream& err(unsigned int Level) = 0;	
	
	virtual ~FkAiClass() { };
};

#endif
