
    // Select all elements with the class "color-swatch"
const swatches = document.querySelectorAll('.color-swatch');

// Add a click event listener to each swatch
swatches.forEach(swatch => {
  swatch.addEventListener('click', function() {
    // Get the theme ID from the data-theme-id attribute of the swatch
    const themeId = this.getAttribute('data-theme-id');
    
    // Add the theme ID as an attribute to the body element
    document.body.setAttribute('data-bs-theme', themeId);
    
    // Save the theme ID value in local storage
    localStorage.setItem('themeId', themeId);
  });
});

// When the page loads, check if a theme ID value is stored in local storage
const storedThemeId = localStorage.getItem('themeId');

// If a theme ID value is stored, set the attribute on the body element
if (storedThemeId) {
  document.body.setAttribute('data-bs-theme', storedThemeId);
}
