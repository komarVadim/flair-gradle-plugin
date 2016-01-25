package flair.gradle.cli

import flair.gradle.dependencies.Sdk
import org.gradle.api.Project
import org.gradle.process.ExecResult

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public class Mxmlc extends AbstractCli
{
	@Override
	public void execute( Project project )
	{
		/*Sdk sdk = new Sdk( project )

		println( sdk.mxmlcPath )
		println( sdk.frameworkPath )

		project.ant.java( jar: sdk.mxmlcPath ,
				fork: true ,
				//dir: sdk.frameworkPath ,
				resultproperty: "mxmlcResult" ,
				outputproperty: "mxmlOutput" ,
				errorproperty: "mxmlError" ,
				failOnError: false ) {

			jvmarg( value: "-Dsun.io.useCanonCaches=false" )
			jvmarg( value: "-Xms32m" )
			jvmarg( value: "-Xmx512m" )
			jvmarg( value: "-Dfile.encoding=UTF-8" )
			jvmarg( value: "-Dflexlib=${ sdk.frameworkPath }" )

			arguments.each {
				println( it )
				arg( value: it )
			}
		}

		println( "result " + project.ant.properties[ "mxmlcResult" ] )
		println( "output " + project.ant.properties[ "mxmlOutput" ] )
		println( "error " + project.ant.properties[ "mxmlError" ] )*/

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream( )

		ExecResult result = project.exec {

			executable new Sdk( project ).mxmlcPath

			arguments.each {

				println( it )
				args it
			}

			standardOutput = outputStream
		}

		println( outputStream.toString( ) )
	}
}