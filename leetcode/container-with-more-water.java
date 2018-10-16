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
 * Stolen from fastest one. The idea is, in the lower side, for all following values smaller than the lower value, their max
 * area must be smaller, because height is lower or equal and width is smaller
 */
class Solution {
    public int maxArea(int[] heights) {
        int startIndex = 0, endIndex = heights.length - 1;
        int maxArea = 0;
        while (startIndex < endIndex) {
        	int h = Math.min(heights[startIndex], heights[endIndex]);
        	maxArea = Math.max(maxArea, h * (endIndex - startIndex));
        	while (heights[startIndex] <= h && startIndex < endIndex) {
        		startIndex++;
        	}
        	while (heights[endIndex] <= h && startIndex < endIndex) {
        		endIndex--;
        	}
        }
        return maxArea;
    }
}
