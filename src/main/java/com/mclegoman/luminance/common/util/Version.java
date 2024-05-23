/*
    Luminance
    Contributor(s): MCLegoMan
    Github: https://github.com/MCLegoMan/Luminance
    Licence: GNU LGPLv3
*/

package com.mclegoman.luminance.common.util;

import com.mclegoman.luminance.client.translation.Translation;
import com.mclegoman.luminance.common.data.Data;
import net.fabricmc.loader.api.ModContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Version implements Comparable<Version> {
	private final String name;
	private final String id;
	private final int major;
	private final int minor;
	private final int patch;
	private final ReleaseType type;
	private final int build;
	private final int dirty;
	private final boolean hasModrinthId;
	private final String modrinthId;
	// dirty should be set to -1 for versions released to modrinth (etc). dirty should only be set (to 1 or higher) if you are building a version that won't get released.
	public static Version create(String name, String id, int major, int minor, int patch, ReleaseType type, int build, int dirty, String modrinthId) {
		return new Version(name, id, major, minor, patch, type, build, dirty, true, modrinthId);
	}
	private Version(String name, String id, int major, int minor, int patch, ReleaseType type, int build, int dirty, boolean hasModrinthId, String modrinthId) {
		this.name = name;
		this.id = id;
		this.major = major;
		this.minor = minor;
		this.patch = patch;
		this.type = type;
		this.build = build;
		this.dirty = dirty;
		this.hasModrinthId = hasModrinthId;
		this.modrinthId = modrinthId;
	}
	public static Version create(String name, String id, int major, int minor, int patch, ReleaseType type, int dirty, int build) {
		return new Version(name, id, major, minor, patch, type, build, dirty, false, "");
	}
	public static Version create(String name, String id, int major, int minor, int patch, ReleaseType type, int build, String modrinthId) {
		return new Version(name, id, major, minor, patch, type, build, -1, true, modrinthId);
	}
	public static Version create(String name, String id, int major, int minor, int patch, ReleaseType type, int build) {
		return new Version(name, id, major, minor, patch, type, build, -1, false, "");
	}
	public String getFriendlyString(boolean full) {
		return full ? getFriendlyString() : (getType().equals(ReleaseType.RELEASE) ? String.format("%s.%s.%s", getMajor(), getMinor(), getPatch()) : getFriendlyString());
	}
	public boolean hasModrinthID() {
		return this.hasModrinthId;
	}
	public String getModrinthID() {
		return this.modrinthId;
	}
	public ModContainer getModContainer() {
		return Data.getModContainer(getID());
	}
	public String getName() {
		return name;
	}
	public String getID() {
		return id;
	}
	public int getMajor() {
		return major;
	}
	public int getMinor() {
		return minor;
	}
	public int getPatch() {
		return patch;
	}
	public ReleaseType getType() {
		return type;
	}
	public int getBuild() {
		return build;
	}
	public int getDirty() {
		return dirty;
	}
	public boolean isDirty() {
		return getDirty() > 0;
	}
	public String getFriendlyString() {
		return Translation.getString("{}.{}.{}-{}.{}{}", getMajor(), getMinor(), getPatch(), Helper.releaseTypeString(getType(), Helper.TranslationType.CODE), getBuild(), (isDirty() ? "+dirty." + getDirty() : ""));
	}
	public String getLoggerPrefix() {
		return Translation.getString("[{} {}]", getName(), getFriendlyString());
	}
	public boolean isDevelopmentBuild() {
		return !type.equals(ReleaseType.RELEASE);
	}
	private Logger getLogger() {
		return LoggerFactory.getLogger(getName());
	}
	@Override
	public int compareTo(Version other) {
		if (major != other.major) {
			return Integer.compare(major, other.major);
		} else if (minor != other.minor) {
			return Integer.compare(minor, other.minor);
		} else if (patch != other.patch) {
			return Integer.compare(patch, other.patch);
		} else if (type != other.type) {
			return type.compareTo(other.type);
		} else {
			return Integer.compare(build, other.build);
		}
	}
	public void sendToLog(LogType logType, String logMessage) {
		if (logType.equals(LogType.INFO)) getLogger().info(Translation.getString("{} {}", getLoggerPrefix(), logMessage));
		if (logType.equals(LogType.WARN)) getLogger().warn(Translation.getString("{} {}", getLoggerPrefix(), logMessage));
		if (logType.equals(LogType.ERROR)) getLogger().error(Translation.getString("{} {}", getLoggerPrefix(), logMessage));
		if (logType.equals(LogType.DEBUG)) getLogger().debug(Translation.getString("{} {}", getLoggerPrefix(), logMessage));
	}
}