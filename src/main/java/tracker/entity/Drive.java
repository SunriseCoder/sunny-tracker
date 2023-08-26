package tracker.entity;

public class Drive {
    private String name;
    private long freeSpaceSize;
    private long totalSpaceSize;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getFreeSpaceSize() {
		return freeSpaceSize;
	}

    public void setFreeSpaceSize(long freeSpaceSize) {
		this.freeSpaceSize = freeSpaceSize;
	}

    public long getTotalSpaceSize() {
		return totalSpaceSize;
	}

    public void setTotalSpaceSize(long totalSpaceSize) {
		this.totalSpaceSize = totalSpaceSize;
	}

    @Override
    public String toString() {
        return String.format("Drive{name: %s, freeSpaceSize: %s, totalSpaceSize: %s}", name, freeSpaceSize, totalSpaceSize);
    }
}
