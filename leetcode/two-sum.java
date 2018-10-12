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
    	// Key is second half's value, value is first half's index
        Map<Integer, Integer> targets = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
        	int num = nums[i];
        	if (targets.containsKey(num)) {
        		return new int[]{targets.get(num), i};
        	}
        	targets.put(target - num, i);
        }
        return new int[]{0, 0};
    }
}