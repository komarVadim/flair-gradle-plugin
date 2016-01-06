package flair.tasks

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import org.gradle.tooling.GradleConnector
import org.gradle.tooling.ProjectConnection

import java.nio.file.*
import java.nio.file.attribute.BasicFileAttributes

import static java.nio.file.LinkOption.NOFOLLOW_LINKS
import static java.nio.file.StandardWatchEventKinds.*

/**
 * @author SamYStudiO on 24/11/2015.
 */
public class AutoGenerateResourceClass extends DefaultTask
{
	protected WatchService watcher

	protected Map<WatchKey , Path> keys

	@SuppressWarnings( "unchecked" )
	static <T> WatchEvent<T> cast( WatchEvent<?> event )
	{
		return ( WatchEvent<T> ) event;
	}

	public AutoGenerateResourceClass()
	{
		group = "generated"
		description = ""
	}

	@TaskAction
	public void watchResources()
	{
		runGenerateResourceClassTask( )

		String moduleName = project.flair.moduleName

		watcher = FileSystems.getDefault( ).newWatchService( )
		keys = new HashMap<WatchKey , Path>( )

		registerDir( project.file( "${ moduleName }/src/main/resources" ).toPath( ) );

		for( ; ; )
		{
			boolean change = false

			WatchKey key
			try
			{
				key = watcher.take( )
			}
			catch( InterruptedException x )
			{
				return
			}

			Path dir = keys.get( key )
			if( dir == null )
			{
				continue;
			}

			for( WatchEvent<?> event : key.pollEvents( ) )
			{
				WatchEvent.Kind kind = event.kind( )

				if( kind == OVERFLOW )
				{
					continue;
				}

				WatchEvent<Path> ev = cast( event )
				Path name = ev.context( )
				Path child = dir.resolve( name )

				if( kind == ENTRY_CREATE )
				{
					try
					{
						if( Files.isDirectory( child , NOFOLLOW_LINKS ) )
						{
							registerDir( child );
						}
					}
					catch( IOException x )
					{}
				}

				change = true
			}

			if( change ) runGenerateResourceClassTask( )

			boolean valid = key.reset( )

			if( !valid )
			{
				keys.remove( key )

				if( keys.isEmpty( ) )
				{
					break
				}
			}
		}
	}

	protected void registerDir( Path root )
	{
		Files.walkFileTree( root , new SimpleFileVisitor<Path>( )
		{
			@Override
			public FileVisitResult preVisitDirectory( Path dir , BasicFileAttributes attrs ) throws IOException
			{
				WatchKey key = dir.register( watcher , ENTRY_CREATE , ENTRY_DELETE , ENTRY_MODIFY )

				keys.put( key , dir )

				return FileVisitResult.CONTINUE
			}
		} )
	}

	protected void runGenerateResourceClassTask()
	{
		ProjectConnection connection = GradleConnector.newConnector( )
				.forProjectDirectory( project.projectDir )
				.connect( )

		connection.newBuild( )
				.forTasks( "generateResourceClass" )
				.run( )

		connection.close( )
	}
}
