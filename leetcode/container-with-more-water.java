/**
 * https://leetcode.com/problems/container-with-most-water/description/
 * Given n non-negative integers a1, a2, ..., an , where each represents a point at coordinate (i, ai). n vertical lines are drawn such that the two endpoints of line i is at (i, ai) and (i, 0). Find two lines, which together with x-axis forms a container, such that the container contains the most water.
 * 
 * Note: You may not slant the container and n is at least 2.
 * 
 * The above vertical lines are represented by array [1,8,6,2,5,4,8,3,7]. In this case, the max area of water (blue section) the container can contain is 49.
 * 
 * Example:
 * 
 * Input: [1,8,6,2,5,4,8,3,7]
 * Output: 49
 *
 * Implementation idea:
 * Take the 1st element as one edge of the container, then start from last element, scan backward until a value greater than
 * the first value is found. For values smaller than the first value, their max container is the container created with the
 * first element, because the height cannot be higher than its own value, and width is the max possible width. Calculate its
 * area, compare with recorded maxArea, and replace if it's greater. After finishing this round of scan, do the same thing
 * the reverse order.
 */
class Solution {
    public int maxArea(int[] heights) {
        int startIndex = 0, endIndex = heights.length - 1;
        boolean reversed = false;
        int maxArea = 0;
        while (startIndex < endIndex) {
        	if (reversed) {
        		int startValue = heights[endIndex];
	        	while (heights[startIndex] <= startValue) {
	        		maxArea = Math.max(maxArea, heights[startIndex] * (endIndex - startIndex));
	        		if (++startIndex >= endIndex) return maxArea;
	        	}
	        	if (endIndex <= startIndex) return maxArea;
        	}
        	else {
        		int startValue = heights[startIndex];
	        	while (heights[endIndex] <= startValue) {
	        		maxArea = Math.max(maxArea, heights[endIndex] * (endIndex - startIndex));
	        		if (--endIndex <= startIndex) return maxArea;
	        	}
	        	if (startIndex >= endIndex) return maxArea;
        	}
        	reversed = !reversed;
        }
        return maxArea;
    }
}
