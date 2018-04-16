package lqcUtils.sort;

import java.util.Arrays;

import org.junit.Test;

public class Sort {

	@Test
	public void test(){
		int[] arr = { 9, 97, 7, 63, 19, 100, 2, 63, 208, 55 };  
//		int[] arr = { 208, 2, 7, 63, 19, 100, 97, 63, 9, 55 };  
		arr = bubbleSort(arr);
	}

	/**
	 * 冒泡排序
	 * 本排序适合：基本有序的数据
	 * 最好时间O(n)	平均时间O(n^2)	最坏时间O(n^2)	辅助空间O(1)	稳定
	 * @param arr
	 * @return
	 */
	public int[] bubbleSort(int... arr){
		for(int i=0 ; i < arr.length-1 ; i++){
			for(int j = arr.length-1 ; j > i ; j--){
				if(arr[j] < arr[j-1]){
					swap(j,j-1,arr);
				}
			}
			System.out.println(Arrays.toString(arr));
		}
		return arr;
	}
	
	/**
	 * 快排
	 * 本排序适合：基本无序的数据
	 * 最好时间O(nlgn)	平均时间O(nlgn)	最坏时间O(n^2)	辅助空间O(lgn)	不稳定
	 * @param arr
	 * @return
	 */
	public int[] quickSort(int low, int high, int...arr ){
		/*
		 * 设置两个指针：i和j，分别指向第一个和最后一个，i像后移动，j向前移动，
		 * 选第一个数为标准（一般这样做，当然快排的关键就是这个“标准”的选取），
		 * 从后面开始，找到第一个比标准小的数，互换位置，然后再从前面，找到第一个比标准大的数，
		 * 互换位置，第一趟的结果就是标准左边的都小于标准，右边的都大于标准（但不一定有序），
		 * 分成两拨后，继续递归的使用上述方法，最终有序！ 
		 * 
		 * 优化：
		 * 1. 如果数组长度小于10，则使用直接插入排序，大于10使用快排
		 * 2. 基准的选择（每次分成等长的的子序列时，分治法效率会达到最大）
		 * 	- 1. 如果数组已经有序，每次分割，只分了一个元素，快排变成冒泡，时间复杂度为O(n^2)，如果数组有序不要选择第一个元素为基准元
		 *  - 2. 随机基准元，随机选一个数做基准元
		 *  - 3. 从数组中取一个元素做基准元
		 */
	
//		if(high - low + 1 >=10){
//			int pivot = partition(low, high, arr);
//			quickSort(low,pivot-1,arr);
//			quickSort(pivot+1,high,arr);
//		} else{
//			insertSort(arr);
//		}
		if(low < high){
			int pivot = partition(low, high, arr);
			quickSort(low,pivot-1,arr);
			quickSort(pivot+1,high,arr);
		}
		return arr;
	}
	
	private int partition(int low, int high,int...arr){
		// 三数中值分割法
//		int m = low + (high - low) / 2; // 数组中间元素的下标
//		if(arr[low] > arr[high]){ // 保证arr[low]较小
//			swap(low,high,arr);
//		}
//		if(arr[m] > arr[high]){ // 保证arr[m]较小
//			swap(m,high,arr);
//		}
//		if(arr[m] > arr[low]){ // 保证arr[low]最小
//			swap(m,low,arr);
//		}
//		int pivot = arr[low]; // 选择基准
		
		
		// 固定基准元
		int pivot = arr[low]; // 选择基准
		
		// 随机基准元 // 为什么不正确
//		 int randomIndex = (int) (Math.random() * (high - low + 1));//取数组中随机下标 
//		 swap(low, randomIndex,arr);                //与第一个数交换 
//		 int pivot = arr[low]; 

		while(low < high){ // low == key结束循环
			while(low < high && arr[high] >= pivot){
				high--;
			}
			arr[low] = arr[high];// arr[high]这个小于key的值放在左边
			while(low < high && arr[low] <= pivot){
				low++;
			}
			arr[high] = arr[low]; // arr[low]这个大于key的值放到右边
		}
		arr[low] = pivot; // low == high时，这就是key的位置
		return low;
	}
	
	private void swap(int index1,int index2,int... arr){
		if(index1 != index2 && index1 <= arr.length && index2 <= arr.length ){
			arr[index1] = arr[index1] ^ arr[index2];
			arr[index2] = arr[index1] ^ arr[index2];
			arr[index1] = arr[index1] ^ arr[index2];
		}
	}
	
	
	
	/**
	 * 直接插入排序
	 * 本排序适合：基本有序的数据
	 * 最好时间O(n)	平均时间O(n^2)	最坏时间O(n^2)	辅助空间O(1)		稳定
	 * 比较数a[i]，与有序列a[0]~a[i-1]比较，从i-1比到0
	 * @param arr
	 * @return
	 */
	public int[] insertSort(int... arr){
		/*
		 * 思路：{原始数组}  -> {有序列，i，i+1，...，n}  -> {有序列，i+1,...,n} -> {有序列}
		 * 使用变量记录a[i], a[i]和前面有序列比较（从后往前比），即a[i]和a[i-1]比，然后和a[i-2]比，
		 * 如果a[i]＜a[i-1]，a[i-1]移到a[i]，然后a[i]和a[i-2]，
		 * 直到a[i]>=a[j]，把临时变量（最初的a[i]）放到a[j+1]的位置
		 * 
		 */
		for(int i=1 ; i<arr.length ;i++){ // 初始循环从0->1，因为第一次循环第一个元素前面没有元素，比较不了也插入不了。
			int tmp,j = 0;
			tmp = arr[i];
			j = i-1;
			while(j>=0 && arr[j]>tmp){
				arr[j+1] = arr[j];
				j--;
			}
			arr[j+1] = tmp; // 循环后执行多一次j--，所以要加1
			System.out.println(Arrays.toString(arr));
		}
		return arr;
	}
	
	/**
	 * 直接选择排序
	 * 本排序适合：基本有序的数据
	 * 最好时间O(n^2)	平均时间O(n^2)	最坏时间O(n^2)	辅助空间O(1)	不稳定
	 * 它的基本思想是：第一次从R[0]~R[n-1]中选取最小值，与R[0]交换，
	 * 第二次从R[1]~R[n-1]中选取最小值，与R[1]交换，....，
	 * 第i次从R[i-1]~R[n-1]中选取最小值，与R[i-1]交换，.....，
	 * 第n-1次从R[n-2]~R[n-1]中选取最小值，与R[n-2]交换，总共通过n-1次，得到一个按排序码从小到大排列的有序序列·
	 * @param arr
	 * @return
	 */
	public int[] selectSort(int...arr){
		for(int i = 0 ; i < arr.length ; i++){
			int index_min = i; // 最小数索引
			for(int j=i+1 ; j < arr.length ; j++){
				if(arr[j] < arr[index_min]){
					index_min = j;
				}
			}
			// 内循环结束，index_min是本次外循环的最小值
			swap(i,index_min,arr);
			System.out.println(Arrays.toString(arr));
		}
		return arr;
	}
	
}
