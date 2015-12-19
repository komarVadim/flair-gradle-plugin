package _appId_.view.core
{
	import feathers.controls.IScreen;

	import org.osflash.signals.Signal;

	/**
	 *
	 */
	public interface IShowHideScreen extends IScreen
	{
		/**
		 *
		 */
		function get shown() : Signal;

		/**
		 *
		 */
		function get hidden() : Signal;

		/**
		 *
		 */
		function show() : void;

		/**
		 *
		 */
		function hide() : void;
	}
}
