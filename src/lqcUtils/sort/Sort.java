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
	 * ð������
	 * �������ʺϣ��������������
	 * ���ʱ��O(n)	ƽ��ʱ��O(n^2)	�ʱ��O(n^2)	�����ռ�O(1)	�ȶ�
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
	 * ����
	 * �������ʺϣ��������������
	 * ���ʱ��O(nlgn)	ƽ��ʱ��O(nlgn)	�ʱ��O(n^2)	�����ռ�O(lgn)	���ȶ�
	 * @param arr
	 * @return
	 */
	public int[] quickSort(int low, int high, int...arr ){
		/*
		 * ��������ָ�룺i��j���ֱ�ָ���һ�������һ����i����ƶ���j��ǰ�ƶ���
		 * ѡ��һ����Ϊ��׼��һ������������Ȼ���ŵĹؼ������������׼����ѡȡ����
		 * �Ӻ��濪ʼ���ҵ���һ���ȱ�׼С����������λ�ã�Ȼ���ٴ�ǰ�棬�ҵ���һ���ȱ�׼�������
		 * ����λ�ã���һ�˵Ľ�����Ǳ�׼��ߵĶ�С�ڱ�׼���ұߵĶ����ڱ�׼������һ�����򣩣�
		 * �ֳ������󣬼����ݹ��ʹ�������������������� 
		 * 
		 * �Ż���
		 * 1. ������鳤��С��10����ʹ��ֱ�Ӳ������򣬴���10ʹ�ÿ���
		 * 2. ��׼��ѡ��ÿ�ηֳɵȳ��ĵ�������ʱ�����η�Ч�ʻ�ﵽ���
		 * 	- 1. ��������Ѿ�����ÿ�ηָֻ����һ��Ԫ�أ����ű��ð�ݣ�ʱ�临�Ӷ�ΪO(n^2)�������������Ҫѡ���һ��Ԫ��Ϊ��׼Ԫ
		 *  - 2. �����׼Ԫ�����ѡһ��������׼Ԫ
		 *  - 3. ��������ȡһ��Ԫ������׼Ԫ
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
		// ������ֵ�ָ
//		int m = low + (high - low) / 2; // �����м�Ԫ�ص��±�
//		if(arr[low] > arr[high]){ // ��֤arr[low]��С
//			swap(low,high,arr);
//		}
//		if(arr[m] > arr[high]){ // ��֤arr[m]��С
//			swap(m,high,arr);
//		}
//		if(arr[m] > arr[low]){ // ��֤arr[low]��С
//			swap(m,low,arr);
//		}
//		int pivot = arr[low]; // ѡ���׼
		
		
		// �̶���׼Ԫ
		int pivot = arr[low]; // ѡ���׼
		
		// �����׼Ԫ // Ϊʲô����ȷ
//		 int randomIndex = (int) (Math.random() * (high - low + 1));//ȡ����������±� 
//		 swap(low, randomIndex,arr);                //���һ�������� 
//		 int pivot = arr[low]; 

		while(low < high){ // low == key����ѭ��
			while(low < high && arr[high] >= pivot){
				high--;
			}
			arr[low] = arr[high];// arr[high]���С��key��ֵ�������
			while(low < high && arr[low] <= pivot){
				low++;
			}
			arr[high] = arr[low]; // arr[low]�������key��ֵ�ŵ��ұ�
		}
		arr[low] = pivot; // low == highʱ�������key��λ��
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
	 * ֱ�Ӳ�������
	 * �������ʺϣ��������������
	 * ���ʱ��O(n)	ƽ��ʱ��O(n^2)	�ʱ��O(n^2)	�����ռ�O(1)		�ȶ�
	 * �Ƚ���a[i]����������a[0]~a[i-1]�Ƚϣ���i-1�ȵ�0
	 * @param arr
	 * @return
	 */
	public int[] insertSort(int... arr){
		/*
		 * ˼·��{ԭʼ����}  -> {�����У�i��i+1��...��n}  -> {�����У�i+1,...,n} -> {������}
		 * ʹ�ñ�����¼a[i], a[i]��ǰ�������бȽϣ��Ӻ���ǰ�ȣ�����a[i]��a[i-1]�ȣ�Ȼ���a[i-2]�ȣ�
		 * ���a[i]��a[i-1]��a[i-1]�Ƶ�a[i]��Ȼ��a[i]��a[i-2]��
		 * ֱ��a[i]>=a[j]������ʱ�����������a[i]���ŵ�a[j+1]��λ��
		 * 
		 */
		for(int i=1 ; i<arr.length ;i++){ // ��ʼѭ����0->1����Ϊ��һ��ѭ����һ��Ԫ��ǰ��û��Ԫ�أ��Ƚϲ���Ҳ���벻�ˡ�
			int tmp,j = 0;
			tmp = arr[i];
			j = i-1;
			while(j>=0 && arr[j]>tmp){
				arr[j+1] = arr[j];
				j--;
			}
			arr[j+1] = tmp; // ѭ����ִ�ж�һ��j--������Ҫ��1
			System.out.println(Arrays.toString(arr));
		}
		return arr;
	}
	
	/**
	 * ֱ��ѡ������
	 * �������ʺϣ��������������
	 * ���ʱ��O(n^2)	ƽ��ʱ��O(n^2)	�ʱ��O(n^2)	�����ռ�O(1)	���ȶ�
	 * ���Ļ���˼���ǣ���һ�δ�R[0]~R[n-1]��ѡȡ��Сֵ����R[0]������
	 * �ڶ��δ�R[1]~R[n-1]��ѡȡ��Сֵ����R[1]������....��
	 * ��i�δ�R[i-1]~R[n-1]��ѡȡ��Сֵ����R[i-1]������.....��
	 * ��n-1�δ�R[n-2]~R[n-1]��ѡȡ��Сֵ����R[n-2]�������ܹ�ͨ��n-1�Σ��õ�һ�����������С�������е��������С�
	 * @param arr
	 * @return
	 */
	public int[] selectSort(int...arr){
		for(int i = 0 ; i < arr.length ; i++){
			int index_min = i; // ��С������
			for(int j=i+1 ; j < arr.length ; j++){
				if(arr[j] < arr[index_min]){
					index_min = j;
				}
			}
			// ��ѭ��������index_min�Ǳ�����ѭ������Сֵ
			swap(i,index_min,arr);
			System.out.println(Arrays.toString(arr));
		}
		return arr;
	}
	
}
