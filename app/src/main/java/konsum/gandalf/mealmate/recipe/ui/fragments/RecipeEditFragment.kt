package konsum.gandalf.mealmate.recipe.ui.fragments

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import konsum.gandalf.mealmate.R
import konsum.gandalf.mealmate.databinding.FragmentRecipeEditBinding
import konsum.gandalf.mealmate.recipe.ui.adapter.IngredientAddAdapter
import konsum.gandalf.mealmate.user.ui.viewmodels.RecipeEditViewModel
import konsum.gandalf.mealmate.utils.events.CustomEvent
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RecipeEditFragment : Fragment() {

    private lateinit var _binding: FragmentRecipeEditBinding
    private val binding
        get() = _binding

    private val viewModel: RecipeEditViewModel by activityViewModels()
    private val navArgs: RecipeEditFragmentArgs by navArgs()

    private var _imageUri: Uri? = null
    private val imageUri
        get() = _imageUri
    private lateinit var activityResultLauncher: ActivityResultLauncher<Intent>

    private lateinit var ingredientAdapter: IngredientAddAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecipeEditBinding.inflate(inflater, container, false)

        fillContent()
        registerActivityForResult()
        initButtons()
        listenToChannels()

        return binding.root
    }

    private fun fillContent() {
        viewModel.getCategories()
        viewModel.getAreas()
        viewModel.getOrCreateRecipe(navArgs.recipe)

        registerAreas()
        registerCategories()
        registerIngredients()
        registerRecipe()
        binding.recipeEditProgress.progressBar.isVisible = false
        // you can just delete a recipe if you have already created it
    }

    private fun initButtons() {
        with(binding) {
            recipeAddIv.setOnClickListener { chooseImage() }
            recipeAddSubmit.setOnClickListener {
                recipeEditProgress.progressBar.isVisible = true
                viewModel.validateCredentialInput(
                    imageUri,
                    binding.recipeAddTitle.text.toString(),
                    binding.recipeAddArea.text.toString(),
                    binding.recipeAddCategory.text.toString(),
                    ingredientAdapter.getIngredients(),
                    binding.recipeAddInstructions.text.toString(),
                    navArgs.createMode
                )
            }
            if (!navArgs.createMode) {
                recipeAddDelete.setOnClickListener {
                    navArgs.recipe?.let { recipe ->
                        val alertDialog = AlertDialog.Builder(requireContext())
                        alertDialog
                            .setTitle("Delete Recipe")
                            .setMessage(
                                "You are about to delete this recipe, do you want to proceed? This change is permanent and cannot be revised"
                            )
                            .setIcon(R.drawable.ph_trash)
                            .setCancelable(false)
                            .setNegativeButton("No") { dialogInterface, _ -> dialogInterface.cancel() }
                            .setPositiveButton("Yes") { _, _ -> viewModel.deleteRecipe(recipe.id) }

                        alertDialog.create().show()
                    }
                }
            } else {
                recipeAddDelete.setOnClickListener {
                    Toast.makeText(
                        requireContext(),
                        "Recipe has not been created yet, hence the recipe can not be deleted",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            }
        }
    }

    private fun registerActivityForResult() {
        activityResultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.data != null && result.resultCode == AppCompatActivity.RESULT_OK) {
                    lifecycleScope.launch {
                        _imageUri = result.data!!.data!!

                        Picasso.get().load(imageUri).into(binding.recipeAddIv)
                    }
                }
            }
    }

    private fun createLaunchIntent() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        activityResultLauncher.launch(intent)
    }

    private fun chooseImage() {
        if (ContextCompat.checkSelfPermission(
                this.requireContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) !=
            PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 1)
        } else {
            createLaunchIntent()
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1) {
            createLaunchIntent()
        }
    }

    private fun registerCategories() {
        viewModel.currentCategories.observe(viewLifecycleOwner) { categories ->
            categories?.let {
                with(binding) {
                    val categoryNames = categories.map { it.name }.toTypedArray()
                    val adapter =
                        ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, categoryNames)
                    recipeAddArea.setAdapter(adapter)
                    recipeAddArea.setText(recipeAddArea.adapter.getItem(0).toString())
                    adapter.filter.filter(null)
                }
            }
        }
    }

    private fun registerAreas() {
        viewModel.currentAreas.observe(viewLifecycleOwner) { areas ->
            areas?.let {
                with(binding) {
                    val areaNames = areas.map { it.name }.toTypedArray()
                    val adapter =
                        ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, areaNames)
                    recipeAddCategory.setAdapter(adapter)
                    recipeAddCategory.setText(recipeAddCategory.adapter.getItem(0).toString())
                    adapter.filter.filter(null)
                }
            }
        }
    }

    private fun registerIngredients() {
        viewModel.currentIngredients.observe(viewLifecycleOwner) { ingredients ->
            ingredients?.let {
                with(binding) {
                    recipeAddIngredientRv.layoutManager =
                        LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                    ingredientAdapter = IngredientAddAdapter(ingredients)
                    recipeAddIngredientRv.adapter = ingredientAdapter
                }
            }
        }
    }

    private fun registerRecipe() {
        viewModel.recipe.observe(viewLifecycleOwner) { recipe ->
            recipe?.let {
                with(binding) {
                    if (recipe.imageUrl.isNotBlank()) {
                        Picasso.get().load(recipe.imageUrl).into(recipeAddIv)
                    }
                    recipeAddTitle.setText(recipe.title)

                    recipeAddIngredientRv.adapter = IngredientAddAdapter(recipe.ingredients)
                    recipeAddIngredientRv.layoutManager =
                        LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                    recipeAddIngredientRv.adapter = IngredientAddAdapter(recipe.ingredients)

                    recipeAddInstructions.setText(recipe.instructions)
                }
            }
        }
    }

    private fun listenToChannels() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.eventFlow.collect { event ->
                when (event) {
                    is CustomEvent.Message -> {
                        Toast.makeText(requireContext(), event.message, Toast.LENGTH_SHORT).show()
                        Navigation.findNavController(binding.root)
                            .navigate(R.id.action_recipeAddFragment_to_userProfileFragment)
                    }
                    is CustomEvent.Error -> {
                        Toast.makeText(requireContext(), event.error, Toast.LENGTH_SHORT).show()
                    }
                    is CustomEvent.ErrorCode -> {
                        binding.recipeEditProgress.progressBar.isVisible = true
                        when (event.code) {
                            1 -> {
                                binding.apply { recipeAddTitle.error = "title should not be empty" }
                            }
                            2 -> {
                                binding.apply { recipeAddArea.error = "no area value was set" }
                            }
                            3 -> {
                                binding.apply { recipeAddCategory.error = "no category value was set" }
                            }
                            4 -> {
                                binding.apply { recipeAddInstructions.error = "instructions should not be empty" }
                            }
                        }
                    }
                }
            }
        }
    }
}
