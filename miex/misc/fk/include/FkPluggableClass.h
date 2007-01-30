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

/* Clase raíz de la jerarquía de módulos */

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

/* Ocultamos la función a los usuarios, basta con que usen 
MAKE_PLUGGABLE(nombre_de_clase, interfaz_que_implementa) 
para incluir la implementación necesaria de giveMeThePlugs() */

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

