package flair.gradle.tasks

import flair.gradle.cli.ICli
import flair.gradle.cli.Mxmlc
import flair.gradle.extensions.FlairProperties
import flair.gradle.variants.Platforms
import flair.gradle.variants.Variant
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputFiles
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
class Compile extends AbstractVariantTask
{
	protected ICli cli = new Mxmlc( )

	@InputFiles
	def Set<File> inputFiles

	@OutputFile
	def File outputFile

	@Input
	def boolean debug

	@Input
	def String mainClass

	@Input
	def List<String> compileOptions

	@Override
	public void setVariant( Variant variant )
	{
		super.variant = variant

		inputFiles = findInputFiles( )
		outputFile = project.file( "${ outputVariantDir }/package/${ variant.getNameWithType( Variant.NamingTypes.UNDERSCORE ) }.swf" )

		debug = extensionManager.getFlairProperty( variant , FlairProperties.DEBUG.name )
		mainClass = extensionManager.getFlairProperty( variant , FlairProperties.COMPILE_MAIN_CLASS.name )
		compileOptions = extensionManager.getFlairProperty( variant , FlairProperties.COMPILE_OPTIONS.name ) as List<String>
	}

	public Compile()
	{
		group = Groups.BUILD.name
		description = ""
	}

	@TaskAction
	public void compile()
	{
		cli.clearArguments( )

		if( variant.platform == Platforms.DESKTOP ) cli.addArgument( "+configname=air" ) else cli.addArgument( "+configname=airmobile" )

		if( debug ) cli.addArgument( "-debug=true" )

		//as files
		cli.addArgument( "-source-path+=${ project.file( "${ outputVariantDir.path }/classes" ) }" )

		//as library files
		cli.addArgument( "-source-path+=${ project.file( "${ outputVariantDir.path }/asLibraries" ) }" )

		//swc library files
		cli.addArgument( "-library-path+=${ project.file( "${ outputVariantDir.path }/libraries" ) }" )

		addConstants( )

		// custom options
		cli.addArguments( compileOptions )

		// swf output
		cli.addArgument( "-output" )
		cli.addArgument( project.file( "${ outputVariantDir }/package/${ variant.getNameWithType( Variant.NamingTypes.UNDERSCORE ) }.swf" ).path )

		// main class
		cli.addArgument( project.file( "${ outputVariantDir }/classes/${ mainClass.split( "\\." ).join( "/" ) }.as" ).path )

		cli.execute( project )
	}

	private addConstants()
	{
		Platforms.values( ).each {

			cli.addArgument( "-define+=PLATFORM::${ it.name.toUpperCase( ) },${ it == variant.platform }" )
		}

		extensionManager.allActivePlatformProductFlavors.each {

			cli.addArgument( "-define+=PRODUCT_FLAVOR::${ it.name.toUpperCase( ) },${ variant.productFlavors.indexOf( it.name ) >= 0 }" )
		}

		extensionManager.allActivePlatformBuildTypes.each {

			cli.addArgument( "-define+=BUILD_TYPE::${ it.name.toUpperCase( ) },${ it.name == variant.buildType }" )
		}
	}

	private List<File> findInputFiles()
	{
		List<File> list = new ArrayList<File>( )

		list.add( project.file( "${ outputVariantDir.path }/classes" ) )
		list.add( project.file( "${ outputVariantDir.path }/libraries" ) )
		list.add( project.file( "${ outputVariantDir.path }/asLibraries" ) )

		return list
	}
}
