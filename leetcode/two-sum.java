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
		int pow2 =(int)Math.pow(2, 12), limit = pow2 / 2;
		short[] positions = new short[pow2];
		for(int i = 0; i < nums.length; i++) {
		    int complement = target - nums[i], complPos = positions[(complement + limit) % pow2];
		    if(complPos != 0) return new int[] { complPos + Short.MAX_VALUE / 2, i };
		    positions[(nums[i] + limit) % pow2] = (short)(i - Short.MAX_VALUE / 2);
		}
		return null;
    }
}