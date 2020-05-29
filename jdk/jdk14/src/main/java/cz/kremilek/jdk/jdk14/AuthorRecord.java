package cz.kremilek.jdk.jdk14;

/**
 * @author Petr Kremensky pkremens@redhat.com
 */
public record AuthorRecord(int id, String name, String topic) {
	static int followers;

	public static String followerCount() {
		return "Followers are "+ followers;
	}

	public String description(){
		return "Author "+ name + " writes on "+ topic;
	}

	public AuthorRecord {
		if (id < 0) {
			throw new IllegalArgumentException( "id must be greater than 0.");
		}
	}
}