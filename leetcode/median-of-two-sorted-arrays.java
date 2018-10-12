/**
 * https://leetcode.com/problems/median-of-two-sorted-arrays/description/
 * 
 * There are two sorted arrays nums1 and nums2 of size m and n respectively.
 * 
 * Find the median of the two sorted arrays. The overall run time complexity should be O(log (m+n)).
 * 
 * You may assume nums1 and nums2 cannot be both empty.
 * 
 * Example 1:
 * 
 * nums1 = [1, 3]
 * nums2 = [2]
 * 
 * The median is 2.0
 * Example 2:
 * 
 * nums1 = [1, 2]
 * nums2 = [3, 4]
 * 
 * The median is (2 + 3)/2 = 2.5
 */
class Solution {
	public double findMedianSortedArrays(int[] nums1, int[] nums2) {
		if (nums1.length > nums2.length) {
			final int[] temp = nums1;
			nums1 = nums2;
			nums2 = temp;
		}

		final int length2 = nums2.length;
		if (nums1.length == 0) {
			return length2 % 2 == 1 ? nums2[length2 / 2] : (nums2[length2 / 2] + nums2[length2 / 2 - 1]) / 2.0;
		}

		try {
			return _findMedianSortedArrays(0, nums1.length - 1, nums1, nums2);
		} catch (final IllegalArgumentException e) {
			return _findMedianSortedArrays(0, nums2.length - 1, nums2, nums1);
		}
	}

	private double _findMedianSortedArrays(final int startIndex, final int endIndex, final int[] nums1,
			final int[] nums2) {
		if (startIndex > endIndex || startIndex < 0 || endIndex >= nums1.length) {
			throw new IllegalArgumentException("");
		}

		final int middleIndex = (startIndex + endIndex) / 2;
		final int middleValue = nums1[middleIndex];
		final int leftSideNumberCount = middleIndex;
		int relativePosition;
		// odd number, left and right side should have the same number of numbers
		if ((nums1.length + nums2.length) % 2 == 1) {
			final int targetPosition = (nums1.length + nums2.length - 1) / 2 - leftSideNumberCount;
			relativePosition = _getRelativePosition(middleValue, targetPosition, nums2);
			if (relativePosition == 0)
				return middleValue;
		}
		// even number, left and right should be the same
		else {
			final int targetPosition = (nums1.length + nums2.length) / 2 - 1 - leftSideNumberCount;
			relativePosition = _getRelativePosition(middleValue, targetPosition, nums2);
			if (relativePosition == 0) {
				int middleRightValue;
				if (middleIndex == nums1.length - 1) {
					middleRightValue = nums2[targetPosition];
				} else if (targetPosition == nums2.length) {
					middleRightValue = nums1[middleIndex + 1];
				} else {
					middleRightValue = Math.min(nums1[middleIndex + 1], nums2[targetPosition]);
				}
				return (middleValue + middleRightValue) / 2.0;
			}
		}
		if (relativePosition > 0) {
			return _findMedianSortedArrays(middleIndex + 1, endIndex, nums1, nums2);
		}
		// too many number on the left side, so should be on the left side
		else {
			return _findMedianSortedArrays(startIndex, middleIndex - 1, nums1, nums2);
		}
	}

	private static int _getRelativePosition(final int number, final int targetPosition, final int[] nums) {
		if (targetPosition > nums.length) {
			return 1;
		}
		if (targetPosition < 0) {
			return -1;
		}

		if (targetPosition == nums.length) {
			return number >= nums[nums.length - 1] ? 0 : 1;
		}

		if (targetPosition == 0) {
			return number <= nums[0] ? 0 : -1;
		}

		if (number < nums[targetPosition - 1]) {
			return 1;
		}

		if (number > nums[targetPosition]) {
			return -1;
		}

		return 0;
	}
}