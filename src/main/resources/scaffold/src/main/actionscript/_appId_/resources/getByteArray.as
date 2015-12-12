package _appId_.resources
{
	import _appId_.view.EnumScreen;

	import flash.utils.ByteArray;

	/**
	 * @author SamYStudiO ( contact@samystudio.net )
	 */
	public function getByteArray( id : String , groupID : String = EnumScreen.MAIN ) : ByteArray
	{
		return getAssetManager( groupID ).getByteArray( id );
	}
}
