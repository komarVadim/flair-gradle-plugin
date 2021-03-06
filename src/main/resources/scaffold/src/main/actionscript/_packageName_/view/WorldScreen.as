package _packageName_.view
{
	import feathers.controls.Button;
	import feathers.controls.Screen;
	import feathers.controls.StackScreenNavigator;
	import feathers.layout.VerticalLayout;

	import flair.logging.debug;

	import starling.events.Event;

	/**
	 * Example screen (you may modify or remove)
	 */
	public class WorldScreen extends Screen
	{
		/**
		 *
		 */
		public function WorldScreen()
		{
			super();

			debug( this , "WorldScreen" );
		}

		/**
		 * @inheritDoc
		 */
		override protected function initialize() : void
		{
			super.initialize();

			var l : VerticalLayout = new VerticalLayout();
			l.horizontalAlign = VerticalLayout.HORIZONTAL_ALIGN_CENTER;
			l.verticalAlign = VerticalLayout.VERTICAL_ALIGN_MIDDLE;
			layout = l;

			var button : Button = new Button();
			button.label = R.string.world;
			button.addEventListener( Event.TRIGGERED , function () : void
			{
				( _owner as StackScreenNavigator ).popScreen();
			} );
			addChild( button );
		}
	}
}
