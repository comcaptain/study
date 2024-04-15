```ts
import simpleGit, { SimpleGit, BranchSummary } from 'simple-git';

async function deleteLocalBranchIfNotPushed(branchName: string, repoPath: string) {
    const git: SimpleGit = simpleGit(repoPath);

    try {
        // Get list of branches with detailed tracking information
        const branchSummary: BranchSummary = await git.branch(['-vv']);

        // Check if the branch exists locally and whether it tracks any remote branch
        const branch = branchSummary.branches[branchName];
        if (!branch) {
            console.log('Branch does not exist locally.');
            return;
        }

        // The branch has a remote tracking branch if it shows a remote in the description
        if (branch.label && branch.label.includes('origin/')) {
            console.log('Branch has been pushed or is tracking a remote branch. Skipping deletion.');
        } else {
            // Branch is not pushed to any remote
            await git.deleteLocalBranch(branchName, true);
            console.log(`Branch '${branchName}' deleted successfully because it was not pushed.`);
        }
    } catch (error) {
        console.error('Error processing the git branches:', error);
    }
}

// Usage example
deleteLocalBranchIfNotPushed('feature-branch', '/path/to/your/repo');


```
