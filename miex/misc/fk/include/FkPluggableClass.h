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

/* Clase ra�z de la jerarqu�a de m�dulos */

#ifndef FKPLUGGABLECLASS_H
#define FKPLUGGABLECLASS_H

//Includes de sistema

//Includes de proyecto


using namespace std;

class FkPluggableClass 
{

friend class ModuleLoaderClass;
friend class AplicationBuilderClass;

public:
	virtual void CreatePlugs() {};
	const char* Qualify() {return QualifiedName; };
	virtual ~FkPluggableClass() {};
private:
	const char* QualifiedName;
};

/* Ocultamos la funci�n a los usuarios, basta con que usen 
MAKE_PLUGGABLE(nombre_de_clase, interfaz_que_implementa) 
para incluir la implementaci�n necesaria de giveMeThePlugs() */

#define MAKE_PLUGGABLE(the_class_name, the_interface)	\
																		\
extern "C" the_interface* NewModule(void* k)				\
{ 																	 	\
	Krn = static_cast<FkAiClass*>(k);				 		\
																		\
	return new the_class_name;		 						 	\
																		\
} 																	 	\
																	 	\
extern "C" char* GetInterfaceName()							\
{																		\
	return #the_interface ;										\
}																		\
																		\
extern "C" void DeleteModule(FkPluggableClass* k)	 	\
{																	 	\
	delete k;													 	\
}																		\

#endif

