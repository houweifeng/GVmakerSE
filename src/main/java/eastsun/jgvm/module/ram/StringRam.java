package eastsun.jgvm.module.ram;

import eastsun.jgvm.module.LavApp;

/**
 * 字符串堆内存模块
 * @author Eastsun
 * @version 1.0 2008/1/19
 */
public final class StringRam implements Ram {

    private byte[] buffer;
    private int offset,  startAddr;

    public StringRam(int size) {
        buffer = new byte[size];
        offset = 0;
    }

    /**
     * 从lav文件中读取一个以0结尾的字符串数组
     * @param source 数据源
     * @return addr 这个数据保存在StringRam中的地址
     */
    public int addString(LavApp source) {
        int addr = offset + startAddr;
        byte b;
        do {
            b = (byte) source.getUint8();
            buffer[offset++] = b;
        } while (b != 0);
        if (offset >= buffer.length * 3 / 4) {
            offset = 0;
        }
        return addr;
    }

    /**
     * 该Ram不允许直接写内存,只能通过addString()方法想里面写数据
     * @throws IndexOutOfBoundsException 调用此方法总是抛出该异常
     * @see #addString(LavApp)
     */
    public void setByte(int addr, byte data) {
        throw new IndexOutOfBoundsException("常字符串不能修改: " + addr);
    }

    /**
     * {@inheritDoc}
     */
    public int size() {
        return buffer.length;
    }

    /**
     * {@inheritDoc}
     */
    public int getRamType() {
        return Ram.RAM_STRING_TYPE;
    }

    /**
     * {@inheritDoc}
     */
    public int getStartAddr() {
        return startAddr;
    }

    /**
     * {@inheritDoc}
     */
    public void setStartAddr(int addr) {
        startAddr = addr;
    }

    /**
     * {@inheritDoc}
     */
    public byte getByte(int addr) {
        return buffer[addr - startAddr];
    }

    /**
     * {@inheritDoc}
     */
    public void clear() {
        offset = 0;
    }
}
