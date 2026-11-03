
# Milestone 4 - Mock Product Data Generator
# Generates 100 products for the technology store.

$names = @(
    "Mechanical Keyboard", "Wireless Gaming Mouse",
    "USB-C Dock", "Noise-Canceling Headset",
    "Portable SSD", "Bluetooth Speaker",
    "USB-C Charging Hub", "Gaming Mouse Pad",
    "External Hard Drive", "Wireless Earbuds",
    "Webcam", "USB Flash Drive",
    "Laptop Stand", "Display Adapter",
    "Studio Headphones", "Keyboard Wrist Rest",
    "Ethernet Adapter", "Portable Microphone",
    "Memory Card Reader", "USB-C Cable"
)

$categories = @(
    "peripherals", "peripherals",
    "connectivity", "audio",
    "storage", "audio",
    "connectivity", "peripherals",
    "storage", "audio",
    "peripherals", "storage",
    "peripherals", "connectivity",
    "audio", "peripherals",
    "connectivity", "audio",
    "storage", "connectivity"
)

$variants = @("Standard", "Plus", "Pro", "Max", "Elite")
$products = @()

for ($i = 0; $i -lt 100; $i++) {
    $typeIndex = $i % $names.Count
    $variantIndex = [math]::Floor($i / $names.Count)

    $product = [ordered]@{
        id = 101 + $i
        name = "$($variants[$variantIndex]) $($names[$typeIndex])"
        description = "Technology accessory - $($variants[$variantIndex]) model of $($names[$typeIndex])"
        dateOfManufacture = (Get-Date "2025-01-01").AddDays($i * 3).ToString("yyyy-MM-dd")
        price = [math]::Round(19.99 + (($i * 7) % 130), 2)
        category = $categories[$typeIndex]
    }

    $products += $product
}

New-Item -ItemType Directory -Force "src/main/resources" | Out-Null

$products |
    ConvertTo-Json -Depth 5 |
    Set-Content -Encoding UTF8 "src/main/resources/products.json"

Write-Host "Generated $($products.Count) products."
