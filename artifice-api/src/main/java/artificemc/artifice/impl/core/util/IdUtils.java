package artificemc.artifice.impl.core.util;

public final class IdUtils {
    private IdUtils() {}

    public static String of(String namespace, String path) {
        validateIdentifier(namespace, path);
        return namespace+":"+path;
    }

    public static String namespaceOf(String id) { return id.contains(":") ? id.split(":")[0] : "minecraft"; }
    public static String pathOf(String id) { return id.contains(":") ? id.split(":",2)[1] : id; }

    public static String prependPath(String id, String pre) { return of(namespaceOf(id), pre+pathOf(id)); }
    public static String appendPath(String id, String suf) { return of(namespaceOf(id), pathOf(id)+suf); }
    public static String wrapPath(String id, String pre, String suf) { return of(namespaceOf(id), pre+pathOf(id)+suf); }

    public static void validateIdentifier(String id) { validateIdentifier(namespaceOf(id), pathOf(id)); }
    public static void validateIdentifier(String namespace, String path) {
        if(!namespace.matches("^[a-z0-9_.-]+$"))
            throw new IllegalArgumentException("Non [a-z0-9_.-] character in namespace of identifier: "+namespace+":"+path);
        if(!path.matches("^[a-z0-9/._-]+$"))
            throw new IllegalArgumentException("Non [a-z0-9/._-] character in path of identifier:  "+namespace+":"+path);
    }
}
