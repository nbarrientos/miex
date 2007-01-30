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

/* Excepción propia de Fk */

#ifndef FKEXCEPTION_H
#define FKEXCEPTION_H

using namespace std;

// Includes de sistema
#include <string>

// Includes de proyecto


// Excepciones propias
#define BadConversionException 4


class FkException {
public:
	
	FkException(const int type, 
					const string& info,
					const string& info2,
					const string& info3);
	

	FkException(const int type, 
					const string& info, 
					const string& info2);	

	FkException(const int type, 
					const string& info);	

	FkException(const int type);	
	
	const int type();	
	
	const string& info();
	
	const string& info2();
	
	const string& info3();
	
	const char* what();
	

private:
	int TheType;
	
	string TheInfo;
	
	string TheInfo2;
	
	string TheInfo3;
};


#endif 
