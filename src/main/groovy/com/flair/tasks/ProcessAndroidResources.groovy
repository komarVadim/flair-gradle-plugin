package com.flair.tasks

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

/**
 * @author SamYStudiO on 24/11/2015.
 */
public class ProcessAndroidResources extends DefaultTask
{
	public ProcessAndroidResources()
	{
		group = "processResources"
		description = ""
	}

	@TaskAction
	public void copy()
	{
		project.getBuildDir( ).deleteDir( )

		String moduleName = project.flair.moduleName
		String androidExcludeResources = project.flair.androidExcludeResources

		project.copy {
			from "${ moduleName }/src/main/assets"
			into "${ project.getBuildDir( ) }/assets"

			includeEmptyDirs = false
		}

		project.copy {
			from "${ moduleName }/src/main/resources/"
			into "${ project.getBuildDir( ) }/resources/"

			exclude androidExcludeResources.split( "," )
			includeEmptyDirs = false
		}

		project.copy {
			from "${ moduleName }/src/main/resources/android/splashs"
			into "${ project.getBuildDir( ) }/"
		}

		project.copy {
			from "${ moduleName }/src/main/resources/android/icons"
			into "${ project.getBuildDir( ) }/icons"
		}
	}
}
