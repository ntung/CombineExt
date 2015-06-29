/**
 * Copyright © 2014-2015:
 * - Martin Peters <martin@freakybytes.net>
 * - Martin Scharm <martin@binfalse.de>
 * 
 * This file is part of the CombineExt library.
 * 
 * CombineExt is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * CombineExt is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with CombineExt. If not, see <http://www.gnu.org/licenses/>.
 */
package de.unirostock.sems.cbext.recognizer;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;

import org.biopax.paxtools.io.BioPAXIOHandler;
import org.biopax.paxtools.util.BioPaxIOException;

import de.binfalse.bflog.LOGGER;
import de.unirostock.sems.cbext.FormatRecognizer;



/**
 * The Class BioPaxFormatizer to recognize BioPax files.
 */
public class BioPaxRecognizer
	extends FormatRecognizer
{
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see de.unirostock.sems.cbext.FormatParser#checkFormat(java.io.File,
	 * java.lang.String)
	 */
	@Override
	public URI getFormatByParsing (File file, String mimeType)
	{
		
		// mime type check
		if (mimeType == null || mimeType.equals ("application/rdf+xml") == false)
			return null;
		
		try
		{
			BioPAXIOHandler handler = new org.biopax.paxtools.io.SimpleIOHandler (); // auto-detects
																																								// Level
			org.biopax.paxtools.model.Model model = handler
				.convertFromOWL (new FileInputStream (file));
			
			return buildUri (IDENTIFIERS_BASE, "biopax");
		}
		catch (IOException | BioPaxIOException e)
		{
			LOGGER.info (e, "file ", file, " seems to be no biopax file..");
		}
		
		// no format could be guessed
		return null;
	}
	
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.unirostock.sems.cbext.FormatRecognizer#getFormatFromMime(java.lang.String
	 * )
	 */
	@Override
	public URI getFormatFromMime (String mime)
	{
		// we cannot decide from just a mime type
		return null;
	}
	
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.unirostock.sems.cbext.FormatRecognizer#getFormatFromExtension(java.lang
	 * .String)
	 */
	@Override
	public URI getFormatFromExtension (String extension)
	{
		if (extension != null && extension.equals ("biopax"))
			return buildUri (IDENTIFIERS_BASE, "biopax");
		return null;
	}
	
}
