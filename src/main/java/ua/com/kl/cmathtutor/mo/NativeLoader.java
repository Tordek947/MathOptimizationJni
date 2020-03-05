package ua.com.kl.cmathtutor.mo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class NativeLoader {

    private static final String NATIVE_LIBS_SUBFOLDER = "/native/";

    public NativeLoader() {
    }

    public void loadLibrary(String library) {
	try {
	    System.load(saveLibrary(library));
	} catch (IOException e) {
	    System.out.println("Could not find library " + library +
		    " as resource, trying fallback lookup through System.loadLibrary");
	    System.loadLibrary(library);
	}
    }

    private String getOSSpecificLibraryName(String library, boolean includePath) {
	String osArch = System.getProperty("os.arch").toLowerCase();
	String osName = System.getProperty("os.name").toLowerCase();
	String name;
	String path;

	if (osName.startsWith("win")) {
	    name = library + ".dll";
	    switch (osArch) {
	    case "x86":
		path = "win-x86/";
		break;
	    case "amd64":
		path = "win-x86_64/";
		break;
	    default:
		throw new UnsupportedOperationException("Platform " + osName + ":" + osArch + " not supported");
	    }
	} else if (osName.startsWith("linux")) {
	    name = "lib" + library + ".so";
	    if (osArch.equalsIgnoreCase("amd64")) {
		path = "linux-x86_64/";
	    } else if (osArch.equalsIgnoreCase("ia64")) {
		path = "linux-ia64/";
	    } else if (osArch.equalsIgnoreCase("i386")) {
		path = "linux-x86/";
	    } else {
		throw new UnsupportedOperationException("Platform " + osName + ":" + osArch + " not supported");
	    }
	} else {
	    throw new UnsupportedOperationException("Platform " + osName + ":" + osArch + " not supported");
	}

	return includePath ? path + name : name;
    }

    private String saveLibrary(String library) throws IOException {
	InputStream in = null;
	OutputStream out = null;
	String savedLibraryAbsolutePath;

	try {
	    String libraryName = getOSSpecificLibraryName(library, true);
	    in = this.getClass().getResourceAsStream(NATIVE_LIBS_SUBFOLDER + libraryName);
	    String tmpDirName = System.getProperty("java.io.tmpdir");
	    File tmpDir = new File(tmpDirName);
	    if (!tmpDir.exists()) {
		tmpDir.mkdir();
	    }
	    File file = File.createTempFile(library + "-", ".tmp", tmpDir);
	    // Clean up the file when exiting
	    file.deleteOnExit();
	    out = new FileOutputStream(file);

	    int cnt;
	    byte buf[] = new byte[16 * 1024];
	    // copy until done.
	    while ((cnt = in.read(buf)) >= 1) {
		out.write(buf, 0, cnt);
	    }
	    System.out.println("Saved libfile: " + file.getAbsoluteFile());
	    savedLibraryAbsolutePath = file.getAbsolutePath();
	} finally {
	    if (in != null) {
		try {
		    in.close();
		} catch (IOException ignore) {
		}
	    }
	    if (out != null) {
		try {
		    out.close();
		} catch (IOException ignore) {
		}
	    }
	}
	return savedLibraryAbsolutePath;
    }
}
