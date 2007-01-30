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

/* Fichero a incluir en toda clase que queramos hacer modular */

#ifndef FKDEVEL_H
#define FKDEVEL_H

//Includes de sistema

//Includes de proyecto
#include "FkAiClass.h"
#include "FkMainPluggableClass.h" // Incluye a su vez a FkPluggableClass.h
#include "FkException.h"

/* Declaramos el puntero al objeto Krn que poroporciona los servicios a los 
módulos. Será "rellenado" en tiempo de ejecución */
FkAiClass* Krn;

//Macros
#define NewCodePlug(the_name, the_type, description)									\
(the_type*)Krn->NewCodePlug(the_name, #the_type, description, Qualify())	

#define NewCode(the_name, the_type, the_path) 					    					\
(the_type*)Krn->NewCode(the_name, #the_type, the_path, Qualify())
	
#define NewDataPlug(the_name, the_type, description)           					\
(the_type)Krn->NewDataPlug(the_name, #the_type, description, Qualify())	

#define DelCode(the_ptr)																		\
Krn->DelCode(the_ptr, #the_ptr, Qualify())

#define Out(the_priority)																		\
Krn->out(the_priority)

#define Err(the_priority)																		\
Krn->err(the_priority)

									
#endif
