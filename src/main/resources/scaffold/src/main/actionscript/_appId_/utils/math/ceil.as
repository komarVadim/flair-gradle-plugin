package _appId_.utils.math
{
	/**
	 *
	 */
	public function ceil( n : Number ) : int
	{
		var ni : int = n;
		return ( n >= 0 && n != ni ) ? ni + 1 : ni;
	}
}
