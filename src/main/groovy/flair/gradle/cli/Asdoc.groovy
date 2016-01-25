package flair.gradle.cli

import flair.gradle.dependencies.Sdk
import org.gradle.api.Project
import org.gradle.process.ExecResult

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public class Asdoc extends AbstractCli
{
	@Override
	public void execute( Project project )
	{
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream( )

		ExecResult result = project.exec {
			executable new Sdk( project ).asdocPath

			arguments.each {

				println( it )
				args it
			}

			standardOutput = outputStream
		}

		println( ">>result1" + result.exitValue + "--" + result.toString( ) )
		println( ">>result2" + outputStream.toString( ) )
	}
}