package flair.gradle.tasks.packaging

import flair.gradle.platforms.Platform
import flair.gradle.tasks.Group
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
class Packaging extends DefaultTask
{
	public Platform platform

	public String productFlavor = ""

	public String buildType = ""

	public Packaging()
	{
		group = Group.PACKAGE.name
		description = ""

		//this.platform = platform
		//this.productFlavor = productFlavor
		//this.buildType = buildType
		//dependsOn Task.PROCESS_ANDROID_RESOURCES
	}

	@TaskAction
	public void packaging()
	{
		println( "test> " + platform + "--" + productFlavor + "--" + buildType )

		ByteArrayOutputStream output = new ByteArrayOutputStream( )

		/*project.exec {
			executable AIRSDKManager.getMXMLCPath( project )
			//args

			standardOutput = output
		}*/

		println( output.toString( ) )
	}
}