#include <FkDevel.h>
#include <cstdio>
#include <string>

class Miex : public FkMainPluggableClass
{

  public:
  	Miex() {};
  	virtual ~Miex() {};

		void CreatePlugs();
	
  	int Main();

	private:
		std::string params;

		static std::string classPath;

};

std::string Miex::classPath = string("\'../../lib/miex.jar:../../lib/JSAP-2.1.jar\'");

int Miex::Main()
{
		string exec = "/usr/bin/java -cp " + classPath + " es.uniovi.aic.miex.run.Miex " + params;
		system(exec.c_str());
		return 0;
}

void Miex::CreatePlugs()
{
	const char * t = (const char *)NewDataPlug("params",const char *, "Parameters for MIEX");
	params = string(t).erase(0,1);
}

MAKE_PLUGGABLE(Miex,FkMainPluggableClass)
