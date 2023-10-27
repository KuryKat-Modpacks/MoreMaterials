# Contributing

Thank you for considering contributing to **KuryKat's More Materials**. We appreciate your interest in helping us
improve and expand the mod.

Before you start contributing, please take a moment to read through this guide to understand how you can contribute
effectively to our project.

## How to Contribute

We welcome contributions in the following areas:

- Bug Fixes: If you find a bug or issue in the mod, please report it on
  our [Issue Tracker](../../issues). If you can fix the issue, feel free to submit
  a [Pull Request](../../pulls).


- Feature Requests: Have an idea for a new feature or enhancement? Let us know by creating a feature request on
  the [Issue Tracker](../../issues). We'll discuss the idea and its feasibility.


- Code Improvements: If you spot areas of the code that can be improved or optimized, feel free to submit
  a [Pull Request](../../pulls)
  with your proposed changes.


- Documentation: Improving the documentation can be a valuable contribution. If you find inconsistencies or have
  suggestions for better documentation, please let us know.

## Getting Started

1. [Fork the repository](../../fork) to your GitHub account.

2. Clone your forked repository to your local machine.

   ```bash
   git clone https://github.com/<YourUsername>/MoreMaterials.git
   ```

3. Create a new branch for your contribution.

   ```bash
   git checkout -b feat/<minecraft-version>/<feature-name>
   ```

   **Rules for Branching:**

    * ALWAYS develop under the feat/ branch
    * `<minecraft-version>` needs to be the correct MC version, e.g. "1.19.2"
    * `<feature-name>` needs to be a valid feature name.
        * e.g. `fix-<issueID>`, which would then be the branch `feat/1.19.2/fix-45`
        * e.g. `cool-feature`, which would then the branch `feat/1.19.2/cool-feature`

4. Make your changes and commit them.
   ```bash
   git commit -a -m "<Emoji> <Type>([Scope]): <Message>"
   ```
    * Note: `([Scope])` is optional

   **Rules for Committing:**

    * All of your commits MUST follow
      the [Conventional Commits specification](https://www.conventionalcommits.org/en/v1.0.0/#specification) and MUST
      use [Gitmojis](https://gitmoji.dev/)
        * **Project Specification**
            * ✨ = Feat
            * ♻️ = Refactor
            * 🐛 = Fix
            * 💚 = CI (mostly the `.github` folder)
            * 👷 = Chore (a.k.a. Build)
            * 📝 = Docs
            * 🧪 = Test
            * 🎨 = Style
            * 💥 = Breaking Changes FOOTER (example below)
    * Those are the most common kinds of commits, but you might need a different one, check the Gitmojis website
      to find more emojis and the Conventional Commits for more types!
    * Example Commit Messages:
        * ``✨ Feat: Create Cool Stuff``
        * ``♻️ Refactor: Improve something nice``
        * ``🐛 Fix(Something): Prevent some issue``
        * Breaking Change Commit Example:
            * ``✨ Feat!: Cool Feature that break things``
            * (Empty Line between message and Footer)
            * ``💥 BREAKING CHANGE: Explanation on why it break things, is it angry?``

5. Push your changes to your forked repository.

   ```bash
   git push origin feat/<minecraft-version>/<feature-name>
   ```

6. Submit a [Pull Request](../../pulls) to the main repository, explaining
   your changes and why they are valuable.

    * **Note:**
      You MUST target the *DEVELOPMENT* branch of the version you're working on when creating the PR, so for example if
      your
      develop branch was ``feat/1.19.2/cool-feature`` you will target the ``1.19.2/dev`` branch with your Pull Request.

## Code Style

Please ensure that your code follows the existing code style and conventions used in the project. I recommend using
IntelliJ IDEA as code formatter.

## Testing

If you are adding new features or fixing bugs, it's essential to test your changes thoroughly. Ensure that your
contributions do not introduce new issues or break existing functionality.

## Licensing

By contributing to **KuryKat's More Materials**, you agree that your contributions will be licensed under the
project's [GNU General Public License v3.0](https://www.gnu.org/licenses/gpl-3.0.en.html).

## Contact

If you have any questions or need further assistance with your contributions, feel free to reach out to us on
our [Discord Server](https://discord.gg/7jBSfARh5f) or through the
project's [GitHub repository](../../).

We appreciate your support and look forward to working together to make **KuryKat's More Materials** even better for the
Minecraft community!