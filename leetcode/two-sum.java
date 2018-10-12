/**
 * https://leetcode.com/problems/two-sum/description/
 * Given an array of integers, return indices of the two numbers such that they add up to a specific target.
 * 
 * You may assume that each input would have exactly one solution, and you may not use the same element twice.
 * 
 * Example:
 * 
 * Given nums = [2, 7, 11, 15], target = 9,
 * 
 * Because nums[0] + nums[1] = 2 + 7 = 9,
 * return [0, 1].
 */
import java.util.*;
class Solution {
    public int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> valuesWithIndex = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
        	valuesWithIndex.put(nums[i], i);
        }

        for (int i = 0; i < nums.length; i++) {
        	int otherHalf = target - nums[i];
        	if (otherHalf == nums[i]) continue;
        	if (valuesWithIndex.containsKey(otherHalf)) {
        		return new int[]{valuesWithIndex.get(otherHalf), i};
        	}
        }
        return new int[]{0, 0};
    }
}