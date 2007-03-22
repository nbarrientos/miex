import es.uniovi.aic.miex.input.Porter;

public class Test
{
	public static void main(String[] args)
	{
		Porter porter = new Porter();

		System.out.println(porter.stripAffixes("training"));

	}


}
