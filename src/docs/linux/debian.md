### Common Debian cli
- `hostname -I` (show current ip information)
- `free -h` see RAM space
- `df -h` (disk free)s ee drives and space
- `rsync -av --remove-source-files ./srcfileordir  ../destination`
  1. -a archive (keep permissions) 
  2. -v verbose


## Mount drives
- identify drive
`sudo lsblk -f`
- create mount point by creating target directory
`sudo mkdir -p /mnt/mydrive`
- temporarily mount drive
`sudo mount /dev/sdb1 /mnt/media1`
- automatically mount on boot
- find uuid of partition
`sudo blkid /dev/sdb1`
- open config file
`sudo nvim /etc/fstab`
- add this line to the bottom of the file (replace uuid, mount path and filesystem type like ext4 or ntfs)
`UUID=your-uuid-here /mnt/mydrive ext4 defaults 0 2`
- test it safely without rebooting
`sudo umount /mnt/mydrive && sudo mount -a`
- NOTE in *raspnas* I created a shell script `mountdrives.sh`





